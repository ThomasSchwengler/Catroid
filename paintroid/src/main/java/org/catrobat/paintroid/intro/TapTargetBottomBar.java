/**
 * Paintroid: An image manipulation application for Android.
 * Copyright (C) 2010-2015 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.paintroid.intro;

import android.view.View;
import android.widget.LinearLayout;

import org.catrobat.paintroid.R;
import org.catrobat.paintroid.WelcomeActivity;
import org.catrobat.paintroid.ui.BottomBarHorizontalScrollView;

public class TapTargetBottomBar extends TapTargetBase {

	public TapTargetBottomBar(LinearLayout tapTargetView, View fadeView, WelcomeActivity activity,
			int bottomScrollBarResourceId) {
		super(tapTargetView, fadeView, activity, bottomScrollBarResourceId);

		bottomScrollBar = (BottomBarHorizontalScrollView)
				activity.findViewById(R.id.pocketpaint_intro_tools_bottom_bar)
						.findViewById(R.id.pocketpaint_bottom_bar_scroll_view);
	}
}
