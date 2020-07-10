import * as $tea from '@alicloud/tea-typescript';
import crypto from 'crypto';

function getSignedStr(request: $tea.Request, resource: string, accessKeySecret: string): string {
  // Find out the "x-oss-"'s address in header of the request
  var tmp: { [key: string]: string } = {};
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

  /**
   * Append a string into a string arrat
   * @example append(["a", "b"], "c") => ["a", "b", "c"]
   * @param strings the string list
   * @param item the string to be appended
   * @return new array
   */
  static append(strings: string[], item: string): string[] {
    return strings.concat(item);
  }

  /**
   * get the map's keys
   * @param m the map
   * @return new array
   */
  static keys(m: { [key: string]: any }): string[] {
    return Object.keys(m);
  }

  /**
   * Join array elements with a spearator string.
   * @example join(["a", "b"], ".") => "a.b"
   * @param strings the string list
   * @param spearator the spearator
   * @return the joined string
   */
  static join(strings: string[], spearator: string): string {
    return strings.join(spearator);
  }

  /**
   * Get date.
   * @example 2006-01-02T15:04:05Z
   * @return date string
   */
  static getDate(): string {
    return new Date().toISOString().replace(/\.\d+/,'');
  }

  /**
   * Get signature with request, accesskeyId, accessKeySecret.
   * @param origin the source map
   * @return signature string
   */
  static getSignature(request: $tea.Request, accessKeyId: string, accessKeySecret: string): string {
    return 'OPENSEARCH ' + accessKeyId + ':' + getSignature(request, accessKeySecret);
  }

  /**
   * Get content MD5.
   * @param content the string which will be calculated
   * @return md5 string
   */
  static getContentMD5(content: string): string {
    return crypto.createHash('md5').update(content).digest('hex');
  }

  /**
   * Splice origin into string with sep.
   * @param origin the source map
   * @param sep the spearator
   * @return the spliced string
   */
  static mapToString(origin: { [key: string]: string }, sep: string): string {
    let ret = '';
    Object.keys(origin).forEach(key => {
      let value = origin[key];
      if (typeof value === 'undefined' || value === null) {
        value = '';
      }
      ret += key + sep + value + ',';
    })
    return ret.slice(0, -1);
  }

}
