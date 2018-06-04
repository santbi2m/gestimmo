/**
 * 
 */
package com.ag2m.gestimmo.metier.ioparam;

import org.joda.time.LocalDateTime;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Nuitée
 * Cet objet représente une nuitée.
 * Il peut représenter aussi un intervalle
 * de temps pour un ensemble de day-use
 * 
 * @author mombaye
 *
 */
@Accessors(fluent = true) 
@Getter @Setter
public class Nuitee {

	private @NonNull LocalDateTime dateCheckin;
	private @NonNull LocalDateTime dateCheckout;
}
