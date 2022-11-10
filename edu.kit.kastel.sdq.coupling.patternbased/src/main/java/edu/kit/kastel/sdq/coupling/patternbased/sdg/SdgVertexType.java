package edu.kit.kastel.sdq.coupling.patternbased.sdg;

/**
 * Represents the different vertex types of a sdg.
 *
 * @author Laura
 *
 */
public enum SdgVertexType {
  CALL,
  ENTR, // METHOD ENTRY
  ACTI, // ACT_IN
  ACTO, // ACT_OUT
  NORM, // NORM
  UNSPECIFIED
}
