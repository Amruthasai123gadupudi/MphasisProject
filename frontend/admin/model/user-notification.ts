import { Role, User } from "./user";

export interface UserNotification {
  id: number;
  user: User;
  message: string;
  active: boolean;
  targetRole: Role;
}
