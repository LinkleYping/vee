package vee.api.http.contract

import javax.ws.rs.Path
import akka.http.scaladsl.server.Route
import com.wavesplatform.UtxPool
import com.wavesplatform.settings.RestAPISettings
import io.netty.channel.group.ChannelGroup
import io.swagger.annotations._
import scorex.BroadcastRoute
import scorex.api.http._

@Path("/contract/broadcast")
@Api(value = "/contract")
case class ContractBroadcastApiRoute(
                                   settings: RestAPISettings,
                                   utx: UtxPool,
                                   allChannels: ChannelGroup)
  extends ApiRoute with BroadcastRoute {
  override val route = pathPrefix("contract" / "broadcast") {
    signedCreate
  }

  @Path("/create")
  @ApiOperation(value = "Broadcasts a signed create conract transaction",
    httpMethod = "POST",
    produces = "application/json",
    consumes = "application/json")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(
      name = "body",
      value = "Json with data",
      required = true,
      paramType = "body",
      dataType = "vee.api.http.contract.SignedCreateContractRequest",
      defaultValue = "{\n\t\"content\": \"contractcontent\",\n\t\"name\": \"contractname\",\n\t\"senderPublicKey\": \"11111\",\n\t\"fee\": 100000\n\t\"timestamp\": 12345678,\n\t\"signature\": \"asdasdasd\"\n}"
    )
  ))
  @ApiResponses(Array(new ApiResponse(code = 200, message = "Json with response or error")))
  def signedCreate: Route = (path("create") & post) {
    json[SignedCreateContractRequest] { contractReq =>
      doBroadcast(contractReq.toTx)
    }
  }
}