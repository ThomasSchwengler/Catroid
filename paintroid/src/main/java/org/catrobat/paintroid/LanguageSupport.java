/**
 *  Paintroid: An image manipulation application for Android.
 *  Copyright (C) 2010-2015 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.paintroid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

public final class LanguageSupport {
	public static final String LANGUAGE_TAG_KEY = "applicationLanguage";
	private static final String DEFAULT_SYSTEM_LANGUAGE = Locale.getDefault().getLanguage();

	private LanguageSupport() {
		throw new IllegalArgumentException();
	}

	public static void setToChosenLanguage(Activity activity) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity
				.getApplicationContext());
		String languageTag = sharedPreferences.getString(LANGUAGE_TAG_KEY, "");

		if (languageTag.equals("")) {
			languageTag = DEFAULT_SYSTEM_LANGUAGE;
		}

		Locale locale = getLocaleFromLanguageTag(languageTag);

		Locale.setDefault(locale);
		setLocale(activity, locale);
		setLocale(activity.getApplicationContext(), locale);
	}

	private static void setLocale(Context context, Locale locale) {
		Resources resources = context.getResources();
		DisplayMetrics displayMetrics = resources.getDisplayMetrics();
		Configuration conf = resources.getConfiguration();
		conf.setLocale(locale);
		resources.updateConfiguration(conf, displayMetrics);
	}

	private static Locale getLocaleFromLanguageTag(String languageTag) {
		if (languageTag.contains("-")) {
			String[] tags = languageTag.split("-");
			return new Locale(tags[0], tags[1]);
		}
		return new Locale(languageTag);
	}
}
