import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:test/test_method_channel.dart';

void main() {
  MethodChannelTest platform = MethodChannelTest();
  const MethodChannel channel = MethodChannel('test');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
