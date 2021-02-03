package pss.clone.pss.domain.blockchain.dao;

import org.apache.ibatis.annotations.Mapper;
import pss.clone.pss.domain.blockchain.domain.BlockChain;

@Mapper
public interface BlockChainMapper {
    void save(BlockChain blockChain);
}
