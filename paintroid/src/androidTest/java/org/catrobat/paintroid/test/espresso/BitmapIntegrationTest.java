/*
 * Paintroid: An image manipulation application for Android.
 * Copyright (C) 2010-2015 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.paintroid.test.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.PaintroidApplication;
import org.catrobat.paintroid.tools.ToolType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.catrobat.paintroid.test.espresso.util.wrappers.ToolBarViewInteraction.onToolBarView;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BitmapIntegrationTest {

	@Rule
	public ActivityTestRule<MainActivity> launchActivityRule = new ActivityTestRule<>(MainActivity.class);

	@Before
	public void setUp() {
		PaintroidApplication.drawingSurface.destroyDrawingCache();

		onToolBarView()
				.performSelectTool(ToolType.BRUSH);
	}

	@Test
	public void drawingSurfaceBitmapIsDisplaySize() {
		final int bitmapWidth = PaintroidApplication.layerModel.getWidth();
		final int bitmapHeight = PaintroidApplication.layerModel.getHeight();

		DisplayMetrics metrics = launchActivityRule.getActivity().getResources().getDisplayMetrics();

		assertEquals(metrics.widthPixels, bitmapWidth);
		assertEquals(metrics.heightPixels, bitmapHeight);
	}
}
