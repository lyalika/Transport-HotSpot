const packageJSON = require('./package.json');
const path = require('path');

const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');
const OptimizeCSSAssetsPlugin = require('optimize-css-assets-webpack-plugin');

const PATHS = {
	build : path.join(__dirname, 'target', 'classes', 'META-INF', 'resources',
			'webjars', packageJSON.name, packageJSON.version)
};

module.exports = {
	entry : {
		app: './src/main/js/packs/application.js',
	},

	output : {
		path : PATHS.build,
		filename: '[name].js'
	},

	// https://github.com/webpack-contrib/mini-css-extract-plugin#minimizing-for-production
	optimization : {
	},

	module : {
		rules : [ {
			test : /\.js$/,
			exclude : /node_modules/,
			use : {
				loader : 'babel-loader',
			},
		}, {
			test : /\.scss$/,
			use : [ {
				loader : MiniCssExtractPlugin.loader,
			}, 'css-loader',
			{
				loader: 'sass-loader',
			    options: {
			        includePaths: ['node_modules'],
			        sourceMap: true,
			    },
			}, 'postcss-loader' ],
		}, ],
	},

	plugins : [ new MiniCssExtractPlugin({
		filename : '[name].css',
		chunkFilename : '[id].css',
	}), ],
};
