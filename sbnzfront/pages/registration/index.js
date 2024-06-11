import axios from "axios"
import { useState } from "react"
import { baseUrl } from "../_app";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import Link from "next/link";
import { toast } from 'sonner';

export default function Registration() {

    const router = useRouter();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [passwordConfirm, setPasswordConfirm] = useState('');
    const [name, setName] = useState('');

    function performRegistration(e) {
        e.preventDefault();

        if (username.length < 1 || password.length < 1 || passwordConfirm.length < 1 || name.length < 1) {
            toast.error('Please fill all fields');
            return;
        }
        if (password !== passwordConfirm) {
            toast.error('Passwords do not match');
            return;
        }
        axios.post(`${baseUrl}/api/user/registration`, { 'username': username, 'password': password, 'name': name, 'type': 'regular' }, { headers: { 'skip': true } })
            .then(res => {
                console.log(res.data)
                router.replace('/login')
            })
            .catch(err => {
                console.log(err)
            })
    }

    return (
        <div>
            <div className="p-20 mt-20 rounded-lg text-center bg-slate-300 max-w-[800px] m-auto">
                <h1 className="text-4xl font-bold text-primary mb-4">Registration</h1>
                <p className="text-2l mb-4 text-muted-foreground">Register for Expert Security System build by Tamara Ilić and Uroš Poček</p>
                <form className="flex flex-col gap-3 items-center" onSubmit={performRegistration}>
                    <Input className="w-60" type='email' placeholder='Enter email' value={username} onChange={(e) => setUsername(e.target.value)} />
                    <Input className="w-60" type='password' placeholder='Enter password' value={password} onChange={(e) => setPassword(e.target.value)} />
                    <Input className="w-60" type='password' placeholder='Repeat password' value={passwordConfirm} onChange={(e) => setPasswordConfirm(e.target.value)} />
                    <Input className="w-60" type='text' placeholder='Enter full name' value={name} onChange={(e) => setName(e.target.value)} />
                    <Button className="mt-4" type="submit">Submit</Button>
                </form>
                <div className="mt-10 flex items-center justify-center gap-2">
                    <span className="text-muted-foreground">{`Already have an account `}</span>
                    <Link href="/login" className="text-primary underline">Login</Link>
                </div>
            </div>
        </div>
    )
}