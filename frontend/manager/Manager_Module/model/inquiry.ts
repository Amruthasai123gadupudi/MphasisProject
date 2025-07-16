import { Property } from './property';
import { User } from './user';

export enum TargetRole {
  SELLER = 'SELLER',
  MANAGER = 'MANAGER'
}

export class Inquiry {
  id!: number;
  message!: string;
  createdAt!: Date;
  buyer!: User;
  property!: Property;

  responseMessage?: string;
  responder?: User;
  respondedAt?: Date;
  targetRole!: TargetRole;
  targetUser!: User;
  scheduleDate?: Date;
}
