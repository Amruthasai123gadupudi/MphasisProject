
import { User } from './user';
import { PropertyType } from './property-type';

export interface Property {
  id: number;
  title: string;
  description: string;
  price: number;
  location: string;
  imageUrl: string;
  approved: boolean;
  createdAt: string; // ISO date string, e.g. "2025-07-06T12:34:56"
  seller: User;
  type: PropertyType;
  manager: User;
}
