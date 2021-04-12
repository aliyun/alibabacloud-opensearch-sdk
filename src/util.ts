import * as $tea from '@alicloud/tea-typescript';
import crypto from 'crypto';

function getSignedStr(request: $tea.Request, resource: string, accessKeySecret: string): string {
  // Find out the "x-oss-"'s address in header of the request
  const tmp: { [key: string]: string } = {};
  Object.keys(request.headers).forEach(key => {
    if (key.toLowerCase().startsWith('x-opensearch-')) {
      tmp[key.toLowerCase()] = request.headers[key];
    }
  })

  const keys = Object.keys(tmp).sort();
  // Get the canonicalizedOSSHeaders
  let canonicalizedOSSHeaders = '';
  keys.forEach(key => {
    canonicalizedOSSHeaders += `${key}:${tmp[key]}\n`;
  });

  // Give other parameters values
  // when sign URL, date is expires
  let date = request.headers['Date'];
  let contentType = request.headers['Content-Type'];
  let contentMd5 = request.headers['Content-MD5'];
  let signStr = `${request.method}\n${contentMd5 || ''}\n${contentType}\n${date}\n${canonicalizedOSSHeaders}${resource}`;
  const hmac = crypto.createHmac('sha1', accessKeySecret);
  hmac.update(signStr);
  return  hmac.digest('base64');
}

function getSignature(request: $tea.Request, accessKeySecret: string): string {
  let resource = request.pathname;
  const params = Object
    .entries<string>(request.query)
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

  return getSignedStr(request, resource, accessKeySecret)
}


export default class Client {
  /*
   * Get signature with request, accesskeyId, accessKeySecret.
   * @return signature string
   * @param request
   * @param accessKeyId
   * @param accessKeySecret
   */
  static getSignature(request: $tea.Request, accessKeyId: string, accessKeySecret: string): string {
    return 'OPENSEARCH ' + accessKeyId + ':' + getSignature(request, accessKeySecret);
  }

  /*
   * Get content MD5.
   * @param content the string which will be calculated
   * @return md5 string
   */
  static getContentMD5(content: string): string {
    return crypto.createHash('md5').update(content).digest('hex');
  }

}
