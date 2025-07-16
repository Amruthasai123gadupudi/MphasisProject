export enum Role {
    BUYER = 'BUYER',
    SELLER = 'SELLER',
    MANAGER = 'MANAGER',
    ADMIN = 'ADMIN'
  }
 
  export interface User {
    id?: number;
    name: string;
    email: string;
    password?: string;  // For create only; avoid showing password otherwise
    role: Role;
    active: boolean;
  }
