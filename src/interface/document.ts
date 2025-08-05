export interface Document {
  timestamp?: number;
  cmd: "add" | "update" | "delete";
  fields: { [key: string]: any };
}
