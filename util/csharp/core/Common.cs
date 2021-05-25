/**
 * This module used for OpenSearch SDK
 */
// This file is auto-generated, don't edit it. Thanks.

using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;
using System.Linq;
using System.Security.Cryptography;

using Tea;
using Tea.Utils;
using System.Text;
using System.Globalization;

namespace AlibabaCloud.OpenSearchUtil
{
    public class Common
    {

        /**
         * Append a string into a string arrat
         * @example append(["a", "b"], "c") => ["a", "b", "c"]
         * @param strings the string list
         * @param item the string to be appended
         * @return new array
         */
        public static List<string> Append(List<string> strings, string item)
        {
            strings.Add(item);
            return strings;
        }

        /**
         * get the map's keys
         * @param m the map
         * @return new array
         */
        public static List<string> Keys(Dictionary<string, object> m)
        {
            return m.Keys.ToList();
        }

        /**
         * Join array elements with a spearator string.
         * @example join(["a", "b"], ".") => "a.b"
         * @param strings the string list
         * @param spearator the spearator
         * @return the joined string
         */
        public static string Join(List<string> strings, string separator)
        {
            return string.Join(separator, strings);
        }

        /**
         * Get date.
         * @example 2006-01-02T15:04:05Z
         * @return date string
         */
        public static string GetDate()
        {
            return DateTime.Now.ToUniversalTime().ToString("yyyy-MM-dd'T'HH:mm:ss'Z'");
        }

        /**
         * Get signature with request, accesskeyId, accessKeySecret.
         * @param origin the source map
         * @return signature string
         */
        public static string GetSignature(TeaRequest request, string accessKeyId, string accessKeySecret)
        {
            return "OPENSEARCH " + accessKeyId + ":" + GetSignature(request, accessKeySecret);
        }

        /**
         * Get content MD5.
         * @param content the string which will be calculated
         * @return md5 string
         */
        public static string GetContentMD5(string content)
        {
            using (MD5 md5Hash = MD5.Create())
            {
                byte[] data = md5Hash.ComputeHash(Encoding.UTF8.GetBytes(content));
                StringBuilder sBuilder = new StringBuilder();
                for (int i = 0; i < data.Length; i++)
                {
                    sBuilder.Append(data[i].ToString("x2"));
                }

                return sBuilder.ToString();
            }
        }

        /**
         * Splice origin into string with sep.
         * @param origin the source map
         * @param sep the spearator
         * @return the spliced string
         */
        public static string MapToString(Dictionary<string, string> origin, string sep)
        {
            string ret = string.Empty;
            foreach (var keypair in origin)
            {
                ret += keypair.Key + sep + keypair.Value + ",";
            }

            return ret.TrimEnd(',');
        }

        /**
         * Compose the string to Sign.
         * @param request TeaRequest
         * @param resource opensearch Uri with path.
         * @param accessKeySecret .
         * @return SignedStr  SignedString.
         */
        internal static string GetSignedStr(TeaRequest request, string resource, string accessKeySecret)
        {
            // Find out the "x-opensearch-"'s address in header of the request
            var tmp = new Dictionary<string, string>();
            foreach (var keypair in request.Headers)
            {
                if (keypair.Key.ToSafeString().ToLower().StartsWith("x-opensearch-"))
                {
                    tmp.Add(keypair.Key.ToSafeString().ToLower(), keypair.Value);
                }
            }

            List<string> sortedKeys = tmp.Keys.ToList();
            sortedKeys.Sort();

            // Get the canonicalizedHeaders
            string canonicalizedHeaders = string.Empty;
            foreach (string key in sortedKeys)
            {
                canonicalizedHeaders += string.Format("{0}:{1}\n", key, tmp[key]);
            }

            // Give other parameters values
            // when sign URL, date is expires
            string contentType = request.Headers.Get("Content-Type");
            string contentMd5 = request.Headers.Get("Content-MD5").ToSafeString();
            string date = request.Headers.Get("Date");
            string method = request.Method.ToUpper();

            string signStr = string.Format("{0}\n{1}\n{2}\n{3}\n{4}{5}", method, contentMd5, contentType, date, canonicalizedHeaders, resource);
            System.Diagnostics.Debug.WriteLine("Alibabacloud.OpenSearchUtil.GetSignature:stringToSign is " + signStr.ToString());
            byte[] signData;
            using (KeyedHashAlgorithm algorithm = CryptoConfig.CreateFromName("HMACSHA1") as KeyedHashAlgorithm)
            {
                algorithm.Key = Encoding.UTF8.GetBytes(accessKeySecret);
                signData = algorithm.ComputeHash(Encoding.UTF8.GetBytes(signStr.ToString().ToCharArray()));
            }
            string signedStr = System.Convert.ToBase64String(signData);
            return signedStr;
        }

        internal static string GetSignature(TeaRequest request, string accessKeySecret)
        {
            string resource = request.Pathname
                .Replace("%2F", "/")
                .Replace("%3F", "?")
                .Replace("%3D", "=")
                .Replace("%26", "&");

            List<string> sortedKeys = request.Query.Keys.ToList();
            var queryPairsList = new List<string>();

            sortedKeys.Sort();

            foreach (string key in sortedKeys)
            {
                string value = request.Query.Get(key);
                if (value == null)
                {
                    continue;
                }

                string valueStr = PercentEncode(value);
                valueStr = valueStr.Replace("'", "%27");
                queryPairsList.Add(key + "=" + valueStr);
            }

            String queryPairsString = Join(queryPairsList,"&");

            if (queryPairsString.Length > 0)
            {
                resource = resource + "?" + queryPairsString;
            }

            return GetSignedStr(request, resource, accessKeySecret);
        }

        internal static string PercentEncode(string value)
        {
            if (value == null)
            {
                return null;
            }
            var stringBuilder = new StringBuilder();
            var text = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~";
            var bytes = Encoding.UTF8.GetBytes(value);
            foreach (char c in bytes)
            {
                if (text.IndexOf(c) >= 0)
                {
                    stringBuilder.Append(c);
                }
                else
                {
                    stringBuilder.Append("%").Append(string.Format(CultureInfo.InvariantCulture, "{0:X2}", (int) c));
                }
            }

            string encodeStr = stringBuilder.ToString()
                .Replace("+", "%20")
                .Replace("*", "%2A")
                .Replace("~", "%7E");

            return encodeStr;
        }
    }
}
