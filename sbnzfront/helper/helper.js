import Router from "next/router";

export function getUserAccessToken() {
    return localStorage.getItem('accessToken');
}
export function getUserRefreshToken() {
    return localStorage.getItem('refreshToken');
}
export function getUserRole() {
    return parseJwt(getUserAccessToken())['role'];
}
export function getUserUsername() {
    return parseJwt(getUserAccessToken())['sub'];
}
export function getUserName() {
    return parseJwt(getUserAccessToken())['name'];
}

function parseJwt(token) {
    if (token == null) {
        return {};
    }
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

export function logOut() {
    localStorage.clear()
    Router.push('/login');
}

export function convertDateToSerbian(date) {
    return new Date(date).toLocaleDateString("sr-RS");
}