import BuildingSection from "@/components/pageComponents/BuildingSection";
import MainSection from "@/components/pageComponents/MainSection";
import SensorSection from "@/components/pageComponents/SensorSection";
import { Inter } from "next/font/google";
import { useEffect, useMemo, useState } from "react";
import axios from "axios";
import { baseUrl } from "./_app";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  const [rooms, setRooms] = useState([]);
  const [products, setProducts] = useState([]);
  const [alarms, setAlarms] = useState([]);
  const [sensors, setSensors] = useState([]);
  const leafRooms = useMemo(() => getLeafRooms(rooms), [rooms])


  useEffect(() => {
    axios.get(`${baseUrl}/api/room/building`)
      .then(res => {
        console.log(res.data);
        setRooms(res.data);
        const building = (res.data).find(r => r.isContainedIn == null)
        axios.get(`${baseUrl}/api/sensor/all?buildingId=${building.id}`,)
          .then(res => {
            console.log(res.data);
            setSensors(res.data);
          })
          .catch(err => console.log(err))
      })
      .catch(err => console.log(err))
  }, [])

  useEffect(() => {
    axios.get(`${baseUrl}/api/product/all_for_building`)
      .then(res => {
        console.log(res.data);
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
          <SensorSection rooms={leafRooms} alarms={[{ 'id': 1, 'type': 'police', 'description': 'Problem sa senzorom' }]} sensors={sensors} setSensors={setSensors} />
        </div>
      </section>
    </main>
  )
}
