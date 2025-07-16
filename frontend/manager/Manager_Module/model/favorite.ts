import { Property } from "./property";
import { User } from "./user";


export class Favorite {
  id!: number;
  buyer!: User;
  property!: Property;
  createdAt!: Date;
}
