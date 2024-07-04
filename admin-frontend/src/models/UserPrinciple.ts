import { Role } from "@constants/UserRole";
import { UserStatus } from "@constants/UserStatus";

export default interface UserPrinciple {
  id: string;
  email: string;
  createdAt: string;
  lastModifiedAt: string;
  role: Role;
  status: UserStatus;
  fullName: string;
  provider: string;
  avatar: string;
}
