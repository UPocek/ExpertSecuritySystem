import BuildingSection from "@/components/pageComponents/BuildingSection";
import MainSection from "@/components/pageComponents/MainSection";
import SensorSection from "@/components/pageComponents/SensorSection";
import { Inter } from "next/font/google";
import { useEffect, useMemo, useState } from "react";
import axios from "axios";
import { baseUrl, wsUrl } from "./_app";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  const [rooms, setRooms] = useState([]);
  const [products, setProducts] = useState([]);
  const [alarms, setAlarms] = useState([]);
  const [sensors, setSensors] = useState([]);

  const [building, setBuilding] = useState({});
  const leafRooms = useMemo(() => getLeafRooms(rooms), [rooms]);

  useEffect(() => {
    const ws = new WebSocket(`${wsUrl}/alarm`);

    ws.onmessage = function (message) {
      console.log(message.data)
      const alarm = JSON.parse(message.data);

      if (alarms.some(a => a.id == alarm.id)) {
        let temp = alarms.find(a => a.id == alarm.id);
        if (temp.type == 'YELLOW' && alarm.type == 'RED') {
          let other = alarms.filter(a => a.id != alarm.id);
          setAlarms([...other, alarm]);
        }
        if (temp.type == 'RED' && alarm.type == 'police') {
          let other = alarms.filter(a => a.id != alarm.id);
          setAlarms([...other, alarm]);
        }
        if (temp.type == 'security' && alarm.type == 'YELLOW') {
          let other = alarms.filter(a => a.id != alarm.id);
          setAlarms([...other, alarm]);
        }
        return;
      }
      setAlarms(prev => [...prev, alarm]);
    }

    return () => {
      if (ws.readyState === 1) {
        ws.close();
      }
    }
  }, [alarms, leafRooms])


  useEffect(() => {
    axios.get(`${baseUrl}/api/room/building`)
      .then(res => {
        setRooms(res.data);
        const building = (res.data).find(r => r.isContainedIn == null);
        setBuilding(building);
        axios.get(`${baseUrl}/api/sensor/all?buildingId=${building.id}`,)
          .then(res => {
            setSensors(res.data);
          })
          .catch(err => console.log(err))
      })
      .catch(err => console.log(err))
  }, [])

  useEffect(() => {
    axios.get(`${baseUrl}/api/product/all_for_building`)
      .then(res => {
        setProducts(res.data);
      })
      .catch(err => console.log(err))
  }, [])


  function getLeafRooms(allRooms) {
    const leafRooms = [];
    for (const room of allRooms) {
      const isParent = allRooms.some(
        (otherRoom) => otherRoom.isContainedIn === room.name
      );

      if (!isParent) {
        leafRooms.push(room);
      }
    }
    return leafRooms;
  }

  return (
    <main>
      <section>
        <div className="grid grid-cols-3">
          <BuildingSection rooms={rooms} setRooms={setRooms} leafRooms={leafRooms} />
          <MainSection rooms={rooms} products={products} />
          <SensorSection building={building} leafRooms={leafRooms} alarms={alarms} setAlarms={setAlarms} sensors={sensors} setSensors={setSensors} />
        </div>
      </section>
    </main>
  )
}
