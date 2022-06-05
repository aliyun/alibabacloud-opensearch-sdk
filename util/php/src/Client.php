<?php

namespace AlibabaCloud\Tea\OpenSearchUtil;

use AlibabaCloud\Tea\RoaUtils\RoaUtils;

/**
 * This module used for OpenSearch SDK.
 */
class Client
{
    /**
     * Append a string into a string arrat.
     *
     * @param array  $strings the string list
     * @param string $item    the string to be appended
     *
     * @return array new array
     *
     * @example append(["a", "b"], "c") => ["a", "b", "c"]
     */
    public static function append($strings, $item)
    {
        array_push($strings, $item);

        return $strings;
    }

    /**
     * get the map's keys.
     *
     * @param array $m the map
     *
     * @return array new array
     */
    public static function keys($m)
    {
        return array_keys($m);
    }

    /**
     * Join array elements with a spearator string.
     *
     * @param array  $strings   the string list
     * @param string $separator the separator
     *
     * @return string the joined string
     *
     * @example join(["a", "b"], ".") => "a.b"
     */
    public static function join($strings, $separator)
    {
        return implode($separator, $strings);
    }

    /**
     * Get date.
     *
     * @return string date string
     *
     * @example 2006-01-02T15:04:05Z
     */
    public static function getDate()
    {
        return gmdate('Y-m-d\\TH:i:s\\Z');
    }

    /**
     * Get signature with request, accesskeyId, accessKeySecret.
     *
     * @param \AlibabaCloud\Tea\Request $request
     * @param string                    $accessKeyId
     * @param string                    $accessKeySecret
     *
     * @return string signature string
     */
    public static function getSignature($request, $accessKeyId, $accessKeySecret)
    {
        $pathname = $request->pathname ? $request->pathname : '';
        $headers  = $request->headers;
        $query    = $request->query ? $request->query : [];

        $get_from_headers = function ($key, $default = '') use ($headers) {
            return isset($headers[$key]) ? $headers[$key] : $default;
        };

        $result = $request->method . "\n" .
            $get_from_headers('accept') . "\n" .
            $get_from_headers('content-md5') . "\n" .
            $get_from_headers('content-type') . "\n" .
            $get_from_headers('date') . "\n";

        $canonicalizedHeaders  = RoaUtils::getCanonicalizedHeaders($headers, 'x-opensearch-');
        $canonicalizedResource = RoaUtils::getCanonicalizedResource($pathname, $query);

        $signString = $result . $canonicalizedHeaders . $canonicalizedResource;

        return 'OPENSEARCH ' .
            $accessKeyId . ':' . base64_encode(
                hash_hmac('sha1', $signString, $accessKeySecret, true)
            );
    }

    /**
     * Get content MD5.
     *
     * @param string $content the string which will be calculated
     *
     * @return string md5 string
     */
    public static function getContentMD5($content)
    {
        return md5($content);
    }

    /**
     * Splice origin into string with sep.
     *
     * @param array  $origin the source map
     * @param string $sep    the spearator
     *
     * @return string the spliced string
     */
    public static function mapToString($origin, $sep)
    {
        $tmp = [];
        foreach ($origin as $k => $v) {
            array_push($tmp, $k . $sep . $v);
        }

        return implode(',', $tmp);
    }
}
