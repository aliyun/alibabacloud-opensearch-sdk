// This file is auto-generated, don't edit it
/**
 * This module used for OpenSearch SDK
 */
import * as $tea from '@alicloud/tea-typescript';
import moment from 'moment';
import crypto from 'crypto';

function getSignedStr(request: $tea.Request, resource: string, accessKeySecret: string): string {
  // Find out the "x-opensearch-"'s address in header of the request
  var tmp: { [key: string]: string } = {};
  Object.keys(request.headers).forEach(key => {
    if (key.toLowerCase().startsWith('x-opensearch-')) {
      tmp[key.toLowerCase()] = request.headers[key];
    }
  })

  const keys = Object.keys(tmp).sort();
  // Get the canonicalizedHeaders
  let canonicalizedHeaders = '';
  keys.forEach(key => {
    canonicalizedHeaders += `${key}:${tmp[key]}\n`;
  });

  // Give other parameters values
  // when sign URL, date is expires
  let date = request.headers['Date'];
  let contentType = request.headers['Content-Type'];
  let contentMd5 = request.headers['Content-MD5'];
  let signStr = `${request.method}\n${contentMd5 || ''}\n${contentType}\n${date}\n${canonicalizedHeaders}${resource}`;
  const hmac = crypto.createHmac('sha1', accessKeySecret);
  hmac.update(signStr);
  let signedStr = hmac.digest('base64');
  return signedStr
}

function getSignature(request: $tea.Request, accessKeySecret: string): string {
  let resource = request.pathname;
  const keys = Object.keys(request.query);
  var queryStrArray =[];
  keys.sort();
  keys.forEach(key => {
    let value = request.query[key];
    if (typeof value === 'undefined' || value === null) {
      return;
    }
    let valueStr = encodeURIComponent(value).replace(/\(/g,"%28").replace(/\)/g,"%29").replace(/'/g, '%27').replace(/\*/g,"%2A").replace(/!/g,"%21");
    queryStrArray.push(`${key}=${valueStr}`);
  })
  if (queryStrArray.length > 0){
    resource = resource+ '?'+ queryStrArray.join("&")
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
    return moment().utc().format('YYYY-MM-DD[T]HH:mm:ss[Z]');
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
