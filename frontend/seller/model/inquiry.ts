// import { User } from './user';

// export type TargetRole = 'SELLER' | 'MANAGER';

// export interface Inquiry {
//   id: number;
//   message: string;
//   responseMessage?: string;
//   createdAt: string;       // ISO string
//   respondedAt?: string;
//   scheduleDate?: string;   // ISO string
//   buyer: User;
//   responder?: User;
//   targetRole: TargetRole;
//   targetUser: User;
// }


import { User } from './user';
import { Property } from './property';  // assuming you have a Property model

export type TargetRole = 'SELLER' | 'MANAGER';

export interface Inquiry {
  id: number;
  message: string;
  responseMessage?: string;
  createdAt: string;       // ISO string
  respondedAt?: string;
  scheduleDate?: string;   // ISO string
  buyer: User;
  responder?: User;
  targetRole: TargetRole;
  targetUser: User;
  property: Property;  // <-- add this
}

