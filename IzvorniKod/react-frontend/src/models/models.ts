export interface Models {
    name: string;
    address: string;
    phoneNumber: number;
    terrains: Terrain[];
    tournaments: Tournament[];
}

export interface Terrain {
    location: string;
    image: File;
    terrainType: TerrainType;
    events: Event[];
}

export interface Tournament {
    name: string;
    location: string;
    date: Date;
    price: string;
    awards: string[];
    description: string;
}

export enum TerrainType {
    INDOOR, OUTDOOR
}

export interface Event {
    date: Date;
    price: string;
}