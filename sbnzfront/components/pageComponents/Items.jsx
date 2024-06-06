import Image from "next/image";

export default function Items() {

    const items = getRandomCombination();

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
        // Axios
    }

    function removeItemFromCart(item) {
        // Axios
    }

    return (
        <div className="flex items-center gap-2 h-28">
            {items.map((item, index) => (
                <div key={index} className="shadow-md flex flex-col items-center justify-center p-4 gap-2 bg-transparent border-none hover:bg-slate-100 relative">
                    <Image src={`/images/${item}.png`} alt="Item" width={48} height={48} />
                    <p>{item}</p>
                    <div className="flex items-center gap-2">
                        <button onClick={() => addItemToCart(item)} className=" h-10 w-10 flex items-center justify-center bg-green-200 p-2 rounded-sm text-sm text-muted-foreground">+</button>
                        <button onClick={() => removeItemFromCart(item)} className=" h-10 w-10 flex items-center justify-center bg-red-200 p-2 rounded-sm text-sm text-muted-foreground">-</button>
                    </div>
                </div>
            ))}
        </div>
    )
}