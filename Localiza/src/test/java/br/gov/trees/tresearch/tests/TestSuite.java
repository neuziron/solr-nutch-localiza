/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.trees.tresearch.tests;

import br.gov.trees.tresearch.model.SolrQueryTest;
import br.gov.trees.tresearch.web.util.SearcherMediatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author thiago.rodrigues
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    SolrQueryTest.class,
    SearcherMediatorTest.class
})
public class TestSuite {
    
}
