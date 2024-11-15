export interface User {
    uuid?: string;
    username: string;
    email: string;
    password: string;
    first_name?: string;
    last_name?: string;
    role?: Role;
}

export enum Role {
    ADMIN = 'admin',
    OWNER = 'owner',
    PLAYER = 'player',
}