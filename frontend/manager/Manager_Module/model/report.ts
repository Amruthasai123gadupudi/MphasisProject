import { User } from "./user";

export class Report {
    id!: number;
  reportType!: string;
  data!: any; // or define a stricter interface if needed
  generatedBy!: User;
  generatedAt!: Date;
}
