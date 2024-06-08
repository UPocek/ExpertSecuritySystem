import { useState } from 'react';
import { baseUrl } from "@/pages/_app";
import axios from "axios";
import Image from "next/image";
import { useEffect } from "react";

export default function Items({ roomId }) {
    const [items, setItems] = useState([]);

    useEffect(() => {
        if (roomId != undefined) {
            axios.get(`${baseUrl}/api/product/all?roomId=${roomId}`).then((res) => {
                console.log(res.data);
                setItems(res.data)
            }).catch(err => console.log(err))
        }

    }, [])



    function getRandomCombination() {
        const categories = ['apple', 'cheese', 'water', 'pizza', 'chicken'];
        const combination = [];

        for (let i = 0; i < 4; i++) {
            const randomIndex = Math.floor(Math.random() * categories.length);
            const randomCategory = categories[randomIndex];
            combination.push(randomCategory);
        }

        return combination;
    }

    function addItemToCart(item) {
        const customerId = 1;
        const price = Math.floor(Math.random() * 42);
        axios.post(`${baseUrl}/api/product_detection?productId=${item.productId}&act=take&customerId=${customerId}&price=${price}`).then((res) => {
            console.log(res.data);
        }).catch(err => console.log(err))
    }

    function removeItemFromCart(item) {
        const customerId = 1;
        const price = Math.floor(Math.random() * 42);
        axios.post(`${baseUrl}/api/product_detection?productId=${item.productId}&act=return&customerId=${customerId}&price=${price}`).then((res) => {
            console.log(res.data);
        }).catch(err => console.log(err))
    }

    return (
        <div className="flex items-center gap-2 h-28">
            {items.map((item, index) => (
                <div key={index} className="shadow-md flex flex-col items-center justify-center p-4 gap-2 bg-transparent border-none hover:bg-slate-100 relative">
                    <Image src={`/images/apple.png`} alt="Item" width={48} height={48} />
                    <p>{item['name']}</p>
                    <div className="flex items-center gap-2">
                        <button onClick={() => addItemToCart(item)} className=" h-10 w-10 flex items-center justify-center bg-green-200 p-2 rounded-sm text-sm text-muted-foreground">+</button>
                        <button onClick={() => removeItemFromCart(item)} className=" h-10 w-10 flex items-center justify-center bg-red-200 p-2 rounded-sm text-sm text-muted-foreground">-</button>
                    </div>
                </div>
            ))}
        </div>
    )
}