export interface Property {
    id: number;
    title: string;
    description: string;
    price: number;
    location: string;
    imageUrl: string;
    approved: boolean;
    createdAt: string;

    seller: {
      id: number;
      name?: string;
      email?: string;
    };
  
    // 🔽 UI-only fields for buyer interaction
    message?: string;
    targetRole?: 'SELLER' | 'MANAGER';
    showInquiryForm?: boolean;
    isFavorite?: boolean;

  }
  