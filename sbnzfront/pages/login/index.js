import axios from "axios"
import { useState } from "react"
import { baseUrl } from "../_app";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import Link from "next/link";
import { toast } from "sonner";

export default function Login() {

    const router = useRouter();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    function performLogin(e) {
        e.preventDefault();
        if (username.length < 1 || password.length < 1) {
            toast.error('Please fill all fields');
            return;
        }
        axios.post(`${baseUrl}/api/user/login`, { 'username': username, 'password': password }, { headers: { 'skip': true } })
            .then(res => {
                localStorage.setItem('accessToken', res.data.accessToken);
                localStorage.setItem('refreshToken', res.data.refreshToken);
                router.replace('/')
            })
            .catch(err => {
                console.log(err)
            })
    }

    return (
        <div>
            <div className="p-20 mt-20 rounded-lg text-center bg-slate-300 max-w-[800px] m-auto">
                <h1 className="text-4xl font-bold text-primary mb-4">Login</h1>
                <p className="text-2l mb-4 text-muted-foreground">Welcome to Expert Security System build by Tamara Ilić and Uroš Poček</p>
                <form className="flex flex-col gap-3 items-center" onSubmit={performLogin}>
                    <Input className="w-60" type='email' placeholder='Enter email' value={username} onChange={(e) => setUsername(e.target.value)} />
                    <Input className="w-60" type='password' placeholder='Enter password' value={password} onChange={(e) => setPassword(e.target.value)} />
                    <Button className="mt-4" type="submit">Submit</Button>
                </form>
                <div className="mt-10 flex items-center justify-center gap-2">
                    <span className="text-muted-foreground">{`Don't have an account `}</span>
                    <Link href="/registration" className="text-primary underline">Register</Link>
                </div>
            </div>
        </div>
    )
}