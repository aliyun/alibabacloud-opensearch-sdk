<?php

namespace AlibabaCloud\Tea\OpenSearchUtil\Tests;

use AlibabaCloud\Tea\OpenSearchUtil\Client;
use AlibabaCloud\Tea\Request;
use PHPUnit\Framework\TestCase;

/**
 * @internal
 * @coversNothing
 */
class ClientTest extends TestCase
{
    public function testAppend()
    {
        $this->assertEquals(['a', 'b'], Client::append(['a'], 'b'));
    }

    public function testKeys()
    {
        $this->assertEquals(
            ['a', 'b'],
            Client::keys(['a' => 'A', 'b' => 'B'])
        );
    }

    public function testJoin()
    {
        $this->assertEquals(
            'a,b,c',
            Client::join(['a', 'b', 'c'], ',')
        );
    }

    /**
     * @throws \Exception
     */
    public function testGetDate()
    {
        $this->assertEquals(20, \strlen(Client::getDate()));
    }

    public function testGetSignature()
    {
        $request           = new Request();
        $request->pathname = '/test/a/b';
        $request->method   = 'GET';
        $request->headers  = ['foo' => 'bar'];
        $request->query    = ['a' => 'b'];
        $this->assertEquals(
            'OPENSEARCH AccessKeyId:mydBvHpkG3gr6Vc/HAfnEpKKK7I=',
            Client::getSignature($request, 'AccessKeyId', 'AccessKeySecret')
        );
    }

    public function testGetContentMD5()
    {
        $this->assertEquals(
            '098f6bcd4621d373cade4e832627b4f6',
            Client::getContentMD5('test')
        );
    }

    public function testMapToString()
    {
        $this->assertEquals(
            'a,b,foo,bar',
            Client::mapToString(['a' => 'b', 'foo' => 'bar'], ',')
        );
    }
}
