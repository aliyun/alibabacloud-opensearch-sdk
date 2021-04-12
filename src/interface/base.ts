export interface Config {
  appName: string;
  endpoint?: string;
  protocol?: string;
  type?: string;
  securityToken?: string;
  accessKeyId?: string;
  accessKeySecret?: string;
  userAgent?: string;
}


export interface Response {
  status?: string;
  requestId?: string;
  errors?: Error[];
}
