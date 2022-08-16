package tk.aizydorczyk.kashubian.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.domain.model.dto.MeaningHierarchyDto
import java.math.BigInteger
import javax.persistence.EntityManager
import javax.persistence.ParameterMode


@Component
class MeaningHierarchyFinder(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {

    @Transactional(readOnly = true)
    fun find(meaningId: Long): MeaningHierarchyDto {
        val derivativeMeaningsIds = findDerivativeMeaningsIds(meaningId)
        val baseMeaningsIds = findBaseMeaningsIds(meaningId)
        val hyperonymsIds = findHyperonymsIds(meaningId)
        val hyponymsIds = findHyponymsIds(meaningId)
        return MeaningHierarchyDto(
                derivativeMeaningsIds = derivativeMeaningsIds,
                baseMeaningsIds = baseMeaningsIds,
                hyperonymsIds = hyperonymsIds,
                hyponymsIds = hyponymsIds)
    }

    private fun findHyponymsIds(meaningId: Long): List<BigInteger> {
        val procedure =
            entityManager.createStoredProcedureQuery("find_hyponym_ids")
        procedure.registerStoredProcedureParameter(
                1,
                Void.TYPE,
                ParameterMode.REF_CURSOR
        )
        procedure.registerStoredProcedureParameter(2, Long::class.java, ParameterMode.IN)
        procedure.setParameter(2, meaningId)
        procedure.execute()

        return procedure.resultList.map { it as BigInteger }
    }

    private fun findHyperonymsIds(meaningId: Long): List<BigInteger> {
        val procedure =
            entityManager.createStoredProcedureQuery("find_hyperonyms_ids")
        procedure.registerStoredProcedureParameter(
                1,
                Void.TYPE,
                ParameterMode.REF_CURSOR
        )
        procedure.registerStoredProcedureParameter(2, Long::class.java, ParameterMode.IN)
        procedure.setParameter(2, meaningId)
        procedure.execute()

        return procedure.resultList.map { it as BigInteger }
    }

    private fun findBaseMeaningsIds(meaningId: Long): List<BigInteger> {
        val procedure =
            entityManager.createStoredProcedureQuery("find_base_meanings_ids")
        procedure.registerStoredProcedureParameter(
                1,
                Void.TYPE,
                ParameterMode.REF_CURSOR
        )
        procedure.registerStoredProcedureParameter(2, Long::class.java, ParameterMode.IN)
        procedure.setParameter(2, meaningId)
        procedure.execute()

        return procedure.resultList.map { it as BigInteger }
    }

    private fun findDerivativeMeaningsIds(meaningId: Long): List<BigInteger> {
        val procedure =
            entityManager.createStoredProcedureQuery("find_derivative_meanings_ids")
        procedure.registerStoredProcedureParameter(
                1,
                Void.TYPE,
                ParameterMode.REF_CURSOR
        )
        procedure.registerStoredProcedureParameter(2, Long::class.java, ParameterMode.IN)
        procedure.setParameter(2, meaningId)
        procedure.execute()

        return procedure.resultList.map { it as BigInteger }
    }
}