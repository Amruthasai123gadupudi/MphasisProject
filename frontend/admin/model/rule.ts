
export enum Role {
  ALL = 'ALL',
  MANAGER = 'MANAGER',
  ADMIN = 'ADMIN',
  BUYER = 'BUYER',
  SELLER = 'SELLER'
}

  
  export interface Rule {
    id: number;
    code: string;
    description: string;
    active: boolean;
    targetRole: Role;
  }
  