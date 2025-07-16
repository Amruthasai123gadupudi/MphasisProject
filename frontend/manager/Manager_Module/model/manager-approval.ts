import { Property } from "./property";
import { User } from "./user";

export class ManagerApproval {
    id!: number;
  manager!: User;
  property!: Property;
  status!: 'PENDING' | 'APPROVED' | 'REJECTED';
  reviewedAt!: Date;
}
