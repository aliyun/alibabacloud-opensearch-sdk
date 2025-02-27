import crypto from "crypto";
import { AxiosRequestConfig } from "axios";

function getSignedStr(conf: AxiosRequestConfig, resource: string, accessKeySecret: string): string {
  // Find out the "x-oss-"'s address in header of the request
  const tmp: { [key: string]: string } = {};
  Object.keys(conf.headers).forEach(key => {
    if (key.toLowerCase().startsWith("x-opensearch-")) {
      tmp[key.toLowerCase()] = conf.headers[key];
    }
  });

  const header = Object.keys(tmp).sort().map(key => `${key}:${tmp[key]}`).join("\n");
  const signStr = [
    conf.method,
    conf.headers["Content-MD5"] || "",
    conf.headers["Content-Type"],
    conf.headers["Date"],
    header,
    resource
  ].join("\n");
  const hmac = crypto.createHmac("sha1", accessKeySecret);
  hmac.update(signStr);
  return hmac.digest("base64");
}

function getSignature(conf: AxiosRequestConfig, accessKeySecret: string): string {
  let resource = conf.url;
  const params = Object
    .entries<string>(conf.params)
    .sort()
    .filter(([, value]) => typeof value !== "undefined" && value !== null)
    .map(([key, value]) => {
      let valueStr = encodeURIComponent(value);
      valueStr = valueStr.replace(/'/g, "%27");
      return `${key}=${valueStr}`;
    });

  if (params.length) {
    resource += `?${params.join("&")}`;
  }

  return getSignedStr(conf, resource, accessKeySecret);
}


export class Util {
  /*
   * @return signature string
   */
  static getSignature(conf: AxiosRequestConfig, accessKeyId: string, accessKeySecret: string): string {
    return "OPENSEARCH " + accessKeyId + ":" + getSignature(conf, accessKeySecret);
  }

  /*
   * Get content MD5.
   * @param content the string which will be calculated
   * @return md5 string
   */
  static getContentMD5(body: { [key: string]: unknown }): string {
    return crypto.createHash("md5").update(JSON.stringify(body)).digest("hex");
  }

  static genNonce(): string {
    return `${Date.now()}${Math.round(Math.random() * 100)}`;
  }
}
