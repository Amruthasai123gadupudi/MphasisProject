import { PropertyType } from "./property-type";
import { User } from "./user";

export class Property {
    id!: number;
    title!: string;
    description!: string;
    price!: number;
    location!: string;
    imageUrl!: string;
    approved!: boolean;
    createdAt!: Date;
    seller!: User;
    manager!: User;
    type!: PropertyType;
}
