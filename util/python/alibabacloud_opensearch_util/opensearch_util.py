# -*- coding: utf-8 -*-

import base64
import hashlib
import hmac
import time
import urllib.parse as parse
from typing import List, Dict, Any

from Tea.request import TeaRequest


def _get_canonicalized_headers(headers):
    canon_keys = []
    for k in headers:
        if k.lower().startswith('x-opensearch-'):
            canon_keys.append(k)
    canon_keys.sort()
    canon_header = ''
    for k in canon_keys:
        canon_header += f'{k.lower()}:{headers[k]}\n'
    return canon_header


def _get_canonicalized_resource(pathname, query):
    canonicalized = parse.quote(pathname)\
        .replace("%2F", "/") \
        .replace("%3F", "?") \
        .replace("%3D", "=") \
        .replace("%26", "&")

    sorted_params = sorted(query)
    params_to_sign = []

    for key in sorted_params:
        value = query[key]
        if value is not None:
            if value == "":
                params_to_sign.append(parse.quote(key))
            else:
                params_to_sign.append("{}={}".format(parse.quote(f'{key}'), parse.quote(f'{value}').replace("/", "%2F")))
    if len(params_to_sign) > 0:
        return canonicalized + "?" + "&".join(params_to_sign)
    return canonicalized


def _get_header(headers, key, default_value=None):
    if key in headers and headers[key] is not None:
        return headers[key]
    return default_value


class OpensearchUtil:
    """
    This module used for OpenSearch SDK
    """

    @staticmethod
    def append(strings: List[str], item: str) -> List[str]:
        """
        Append a string into a string arrat
        @example append(["a", "b"], "c") => ["a", "b", "c"]
        @param strings: the string list
        @param item: the string to be appended
        @return: new array
        """
        string_list = strings.copy()
        string_list.append(item)
        return string_list

    @staticmethod
    def keys(m: Dict[str, Any]) -> List[str]:
        """
        get the map's keys
        @param m: the map
        @return: new array
        """
        return list(m.keys())

    @staticmethod
    def join(strings: List[str], separator: str) -> str:
        """
        Join array elements with a spearator string.
        @example join(["a", "b"], ".") => "a.b"
        @param strings: the string list
        @param separator: the separator
        @return: the joined string
        """
        return separator.join(strings)

    @staticmethod
    def get_date() -> str:
        """
        Get date.
        @example 2006-01-02T15:04:05Z
        @return: date string
        """
        return time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime())

    @staticmethod
    def get_signature(request: TeaRequest, access_key_id: str, access_key_secret: str) -> str:
        """
        Get signature with request, accesskeyId, accessKeySecret.
        @param request: the object of tea request
        @param access_key_id: accesskey id
        @param access_key_secret: accesskey secret
        @return: signature string
        """
        method, pathname, headers, query = request.method, request.pathname, request.headers, request.query

        content_md5 = _get_header(headers, "Content-MD5", "")
        content_type = _get_header(headers, "Content-Type", "")
        date = _get_header(headers, "Date", "")

        canon_headers = _get_canonicalized_headers(headers)
        canon_resource = _get_canonicalized_resource(pathname, query)

        sign_str = f'{method}\n{content_md5}\n{content_type}\n{date}\n{canon_headers}{canon_resource}'

        hash_val = hmac.new(access_key_secret.encode('utf-8'), sign_str.encode('utf-8'), hashlib.sha1).digest()
        signature = base64.b64encode(hash_val).decode('utf-8')
        return f'OPENSEARCH {access_key_id}:{signature}'

    @staticmethod
    def get_content_md5(content: str) -> str:
        """
        Get content MD5.
        @param content: the string which will be calculated
        @return: md5 string
        """
        md5 = hashlib.md5(content.encode('utf-8'))
        return md5.hexdigest()

    @staticmethod
    def map_to_string(origin: Dict[str, str], sep: str) -> str:
        """
        Splice origin into string with sep.
        @param origin: the source map
        @param sep: the spearator
        @return: the spliced string
        """
        ret = [f'{k}{sep}{v if v is not None else ""}' for k, v in origin.items()]
        return ','.join(ret)
