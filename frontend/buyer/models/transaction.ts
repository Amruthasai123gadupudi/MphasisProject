import { Property } from "./property.model";

export interface Transaction {
    id: number;
    property: Property;
    buyer: {
      id: number;
      name?: string;
      email?: string;
    };
    seller: {
      id: number;
      name?: string;
      email?: string;
    };
    status: 'INITIATED' | 'IN_PROGRESS' | 'COMPLETED';
    closedAt?: string;
    amount: number;
    manager?: {
      id: number;
      name?: string;
      email?: string;
    };
   
  }
  