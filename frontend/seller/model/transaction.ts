import { Property } from "./property";
import { User } from "./user";

 
export class Transaction {
    id!: number;
    property!: Property;
    buyer!: User;
    seller!: User;
    status: 'INITIATED' | 'IN_PROGRESS' | 'COMPLETED' = 'INITIATED';
    closedAt?: string;
    amount!: number;
    manager?: User;
 
    constructor(init?: Partial<Transaction>) {
      Object.assign(this, init);
    }
  }