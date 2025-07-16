import { Property } from './property';
import { User } from './user';

export interface Transaction {
  id: number;
  property: Property;
  buyer: User;
  seller: User;
  status: TransactionStatus;
  closedAt: string | null; // ISO date string or null
  amount: number;
  manager: User;
}

export type TransactionStatus = 'INITIATED' | 'IN_PROGRESS' | 'COMPLETED';
