"use client";

import { useState } from "react";
import { fetcher } from "../../../lib/api";

type LoginRequest = {
    email: string;
    password: string;
};

type Role = "USER" | "STAFF" | "ADMIN";

type AuthResponse = {
    id: number;
    username: string;
    email: string;
    role: Role;
};

export default function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    async function handleLogin(e: React.FormEvent) {
        e.preventDefault();
        setLoading(true);
        setError(null);

         try {
            const data = await fetcher<AuthResponse>("/api/accounts/login", {
                method: "POST",
                body: JSON.stringify({
                    email,
                    password,
                } satisfies LoginRequest),
                credentials: "include",
            });

            if (data.role === "STAFF" || data.role === "ADMIN") {
                window.location.href = "/staff";
            } else {
                window.location.href = "/user";
            }

            console.log("Logged in:", data);

        } catch (err: any) {
            setError(err.message ?? "Login failed");
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-50">
            <div className="w-full max-w-md bg-white p-8 rounded-2xl shadow-lg border">
                <h2 className="text-2xl font-semibold mb-6 text-center text-black">
                    Login
                </h2>

                <form onSubmit={handleLogin} className="space-y-4">
                    <div>
                        <label className="text-sm font-medium text-black">Email</label>
                        <input
                            type="email"
                            className="w-full mt-1 px-3 py-2 border rounded-lg focus:outline-none focus:ring text-black"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <label className="text-sm font-medium text-black">Password</label>
                        <input
                            type="password"
                            className="w-full mt-1 px-3 py-2 border rounded-lg focus:outline-none focus:ring text-black"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    {error && (
                        <p className="text-red-500 text-sm">{error}</p>
                    )}

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-black text-white py-2 rounded-lg hover:bg-gray-800 transition disabled:opacity-50"
                    >
                        {loading ? "Logging in..." : "Login"}
                    </button>

                    <div className="text-center text-sm text-gray-600">
                        Don't have an account?{" "}
                        <a href="/register" className="text-blue-500 hover:underline">
                            Register here
                        </a>
                    </div>
                </form>
            </div>
        </div>
    );
}