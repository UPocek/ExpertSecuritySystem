import BuildingSection from "@/components/pageComponents/BuildingSection";
import MainSection from "@/components/pageComponents/MainSection";
import SensorSection from "@/components/pageComponents/SensorSection";
import { Inter } from "next/font/google";
import { useMemo, useState } from "react";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  const [rooms, setRooms] = useState([]);
  const [alarms, setAlarms] = useState([]);
  const [sensors, setSensors] = useState([]);
  const leafRooms = useMemo(() => getLeafRooms(rooms), [rooms])

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
          <MainSection rooms={rooms} />
          <SensorSection rooms={rooms} alarms={[{ 'id': 1, 'type': 'police', 'description': 'Problem sa senzorom' }]} sensors={[{ 'id': 1, 'type': 'smoke', 'roomId': 1, 'low': 1, 'high': 5 }]} setSensors={setSensors} />
        </div>
      </section>
    </main>
  )
}
