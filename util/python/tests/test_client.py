import unittest

from alibabacloud_opensearch_util.opensearch_util import OpensearchUtil
from Tea.request import TeaRequest


class TestClient(unittest.TestCase):
    def test_get_date(self):
        result = OpensearchUtil.get_date()
        self.assertEqual(20, len(result))

    def test_keys(self):
        m = {'k1': 'v1', 'k2': 'v2'}
        result = OpensearchUtil.keys(m)
        self.assertEqual(['k1', 'k2'], result)

    def test_map_to_string(self):
        m = {'k1': 'v1', 'k2': 'v2'}
        result = OpensearchUtil.map_to_string(m, '|')
        self.assertEqual('k1|v1,k2|v2', result)

    def test_join(self):
        result = OpensearchUtil.join(['a', 'b'], '.')
        self.assertEqual('a.b', result)

    def test_append(self):
        result = OpensearchUtil.append(['a', 'b'], 'c')
        self.assertEqual(['a', 'b', 'c'], result)

    def test_get_content_md5(self):
        result = OpensearchUtil.get_content_md5('test')
        self.assertEqual('098f6bcd4621d373cade4e832627b4f6', result)

    def test_get_signature(self):
        request = TeaRequest()
        request.pathname = '/test/a/b'
        request.query = {
            'key': 'test'
        }
        request.headers = {
            'content-md5': '098f6bcd4621d373cade4e832627b4f6'
        }
        result = OpensearchUtil.get_signature(request, 'AccessKeyId', 'AccessKeySecret')
        self.assertEqual('OPENSEARCH AccessKeyId:Puo3jkoIu0S1F8x3jHDxSpNhnQU=', result)
