export interface Inquiry {
    id: number;
    message: string;
    createdAt: string;
    responseMessage: string;
    respondedAt: string;
    targetRole: 'SELLER' | 'MANAGER';
  
    buyer: User;
    property: Property;
    responder: User | null;
    targetUser: User;
  }
  
  export interface User {
    id: number;
    name: string;
    email: string;
    role: 'BUYER' | 'SELLER' | 'MANAGER' | 'ADMIN';
    active: boolean;
  }
  
  export interface Property {
    id: number;
    title: string;
    description: string;
    price: number;
    location: string;
    imageUrl: string;
    approved: boolean;
    createdAt: string;
  }
  