import { useState, useEffect } from 'react';
import { baseUrl } from "@/pages/_app";
import axios from "axios";
import Image from "next/image";

export default function Items({ roomId }) {
    const [items, setItems] = useState([]);

    useEffect(() => {
        if (roomId != undefined) {
            axios.get(`${baseUrl}/api/product/all?roomId=${roomId}`)
                .then((res) => {
                    console.log(res.data);
                    setItems(res.data)
                }).catch(err => console.log(err))
        }

    }, [roomId])

    function addItemToCart(item) {
        const customerId = 1;
        const price = Math.floor(Math.random() * 42);
        axios.post(`${baseUrl}/api/product_detection?productId=${item.productId}&act=take&customerId=${customerId}&price=${price}`)
            .then((res) => {
                console.log(res.data);
            }).catch(err => console.log(err))
    }

    function removeItemFromCart(item) {
        const customerId = 1;
        const price = Math.floor(Math.random() * 42);
        axios.post(`${baseUrl}/api/product_detection?productId=${item.productId}&act=return&customerId=${customerId}&price=${price}`)
            .then((res) => {
                console.log(res.data);
            }).catch(err => console.log(err))
    }

    return (
        <div className="flex flex-wrap items-center gap-2 h-28 mb-10">
            {items.map((item, index) => (
                <div key={item.name} className="max-w-[120px] shadow-md flex flex-col items-center justify-center p-4 gap-2 bg-transparent border-none hover:bg-slate-100 relative">
                    <Image src={`/images/${item.name.toLowerCase()}.png`} alt="Item" width={48} height={48} />
                    <p className='text-sm text-muted-foreground overflow-hidden text-ellipsis max-w-[90px] text-nowrap'>{item['name']}</p>
                    <div className="flex items-center gap-2">
                        <button onClick={() => addItemToCart(item)} className=" h-10 w-10 flex items-center justify-center bg-green-200 p-2 rounded-sm text-sm text-muted-foreground">+</button>
                        <button onClick={() => removeItemFromCart(item)} className=" h-10 w-10 flex items-center justify-center bg-red-200 p-2 rounded-sm text-sm text-muted-foreground">-</button>
                    </div>
                </div>
            ))}
        </div>
    )
}