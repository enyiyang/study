import 'package:tutorials/request/model/common/common_param.dart';

class SecurityCodeRequestParam {

  CommonParam common = CommonParam();

  String clientId = "";

  @override
  String toString() {
    return 'SecurityCodeRequestParam{common: $common, clientId: $clientId}';
  }
}
