const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8089';

export async function fetcher<T>(endpoint: string, options?: RequestInit): Promise<T> {
    const response = await fetch(`${API_URL}${endpoint}`, {
        headers: {
            'Content-Type': 'application/json',
            ...(options?.headers || {}),
        },
        credentials: 'include',
        ...options,
    });
    if (!response.ok) {
        const errorBody = await response.text().catch(() => null);

        throw new Error(
            `Request failed: ${response.status} ${response.statusText} - ${errorBody}`
        );
    }
    return (await response.json() as T);
}