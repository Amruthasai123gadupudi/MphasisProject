import { PropertyType } from "./property-type";
import { User } from "./user";


export interface Property {
    id: number;
    title: string;
    description: string;
    price: number;
    location: string;
    imageUrl: string;
    approved: boolean;
    createdAt?: string;
    seller: User;
    manager: User;
    type: PropertyType;
}
