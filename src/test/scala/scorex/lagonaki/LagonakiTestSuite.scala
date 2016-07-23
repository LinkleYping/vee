package scorex.lagonaki

import org.scalatest.{BeforeAndAfterAll, Sequential}
import scorex.lagonaki.TestingCommons._
import scorex.lagonaki.integration._
import scorex.lagonaki.integration.api._
import scorex.lagonaki.unit._
import scorex.transaction.state.StateTest
import scorex.transaction.state.database.blockchain.BlockTreeSpecification

class LagonakiTestSuite extends Sequential(
  //unit tests
  new MessageSpecification
  , new BlockSpecification
  //  , new BlockStorageSpecification
  , new WalletSpecification
  , new BlockGeneratorSpecification
  , new BlockchainSynchronizerSpecification
  , new CoordinatorSpecification
  , new ScoreObserverSpecification
  , new BlockTreeSpecification
  , new StateTest
  , new StoredStateSpecification
  // API tests
  , new UtilsAPISpecification
  , new PeersAPISpecification
  , new WalletAPISpecification
  , new AddressesAPISpecification
  , new TransactionsAPISpecification
  , new PaymentAPISpecification
  , new BlockAPISpecification
  //integration tests - slow!
  //, new ValidChainGenerationSpecification

) with BeforeAndAfterAll {

  override protected def beforeAll() = {
    Runtime.getRuntime.exec("rm -rf /tmp/scorex-tests")
  }

  override protected def afterAll() = {
    applications.foreach(_.stopAll())
  }
}
