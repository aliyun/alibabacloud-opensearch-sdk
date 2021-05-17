export interface Config {
  appName: string;
  endpoint: string;
  securityToken?: string;
  accessKeyId?: string;
  accessKeySecret?: string;
  userAgent?: string;
  timeout?: number;
}


export interface Response {
  status?: string;
  request_id?: string;
  errors?: Error[];
}
