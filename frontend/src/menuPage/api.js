const API_BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

export async function createUser(name, initialBalance = 1000) {
  const url = `${API_BASE}/api/users`;
  const res = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ name, initialBalance }),
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || 'Failed to create user');
  }
  return res.json();
}

export async function getUserById(id) {
  const url = `${API_BASE}/api/users/${id}`;
  const res = await fetch(url);
  if (!res.ok) throw new Error('User not found');
  return res.json();
}

export async function getUserByName(name) {
  const url = `${API_BASE}/api/users/search?name=${encodeURIComponent(name)}`;
  const res = await fetch(url);
  if (!res.ok) throw new Error('User not found');
  return res.json();
}

export default {
  createUser,
  getUserById,
};
