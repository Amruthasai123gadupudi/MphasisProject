import { Property } from "./property";
import { User } from "./user";

export class Transaction {
    id!: number;
  property!: Property;
  buyer!: User;
  seller!: User;
  status!: 'INITIATED' | 'IN_PROGRESS' | 'COMPLETED';
  closedAt?: Date;
}
